import io
import json
import re
from datetime import datetime
from pypdf import PdfReader
import streamlit as st

EMAIL_RE = re.compile(r"\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}\b", re.I)
PHONE_RE = re.compile(r"(?<!\w)(?:\+?\d[\d ()-]{7,}\d)(?!\w)")
DATE_RE = re.compile(r"\b(?:\d{4}-\d{2}-\d{2}|\d{1,2}[/-]\d{1,2}[/-]\d{2,4})\b")
AMOUNT_RE = re.compile(r"(?:₹|INR\s?|\$|USD\s?|€)\s?\d[\d,]*(?:\.\d{1,2})?", re.I)
REFERENCE_RE = re.compile(r"\b(?:INV|REF|CASE|TICKET|REQ|ORDER)[-_ ]?\d{3,}\b", re.I)

def classify_request(text):
    value = text.lower()
    if any(word in value for word in ("invoice", "payment", "refund", "billing", "amount due")):
        return "invoice_or_payment"
    if any(word in value for word in ("error", "not working", "issue", "bug", "support", "unable to")):
        return "support_request"
    if any(word in value for word in ("application", "apply for", "candidate", "enrollment", "registration")):
        return "application"
    return "general_request"

def extract_record(text, document_name="document"):
    emails = list(dict.fromkeys(EMAIL_RE.findall(text)))
    phone_candidates = [match.strip() for match in PHONE_RE.findall(text)]\n    phones = list(dict.fromkeys(value for value in phone_candidates if len(re.sub(r"\\D", "", value)) >= 10 and not DATE_RE.fullmatch(value.strip())))
    dates = list(dict.fromkeys(DATE_RE.findall(text)))
    amounts = list(dict.fromkeys(AMOUNT_RE.findall(text)))
    refs = list(dict.fromkeys(match.upper().replace(" ", "-") for match in REFERENCE_RE.findall(text)))
    record = {
        "document_name": document_name,
        "request_type": classify_request(text),
        "email": emails[0] if emails else None,
        "phone": phones[0] if phones else None,
        "dates": dates,
        "amounts": amounts,
        "reference_ids": refs,
        "needs_human_review": not bool(text.strip()) or not (emails or refs or amounts),
        "processed_at_utc": datetime.utcnow().replace(microsecond=0).isoformat() + "Z"
    }
    return record

def read_upload(upload):
    raw = upload.getvalue()
    if upload.name.lower().endswith(".pdf"):
        reader = PdfReader(io.BytesIO(raw))
        return "\n".join(page.extract_text() or "" for page in reader.pages)
    if upload.name.lower().endswith(".txt"):
        return raw.decode("utf-8", errors="replace")
    raise ValueError("Only PDF and TXT files are supported.")

st.set_page_config(page_title="Forest AI", layout="wide")
st.title("Forest AI")
st.caption("Document intake, field extraction, and review-ready structured output")
files = st.file_uploader("Upload documents", type=["pdf", "txt"], accept_multiple_files=True)
if files:
    for file in files:
        try:
            text = read_upload(file)
            record = extract_record(text, file.name)
            st.subheader(file.name)
            st.write(f"**Request type:** {record['request_type']}")
            if record["needs_human_review"]:
                st.warning("Human review recommended: key identifiers may be missing.")
            else:
                st.success("Initial extraction completed. Verify all fields before use.")
            st.json(record)
            st.download_button(
                f"Download {file.name} JSON",
                data=json.dumps(record, indent=2, ensure_ascii=False),
                file_name=f"{file.name.rsplit('.', 1)[0]}.json",
                mime="application/json",
                key=file.name
            )
        except Exception as exc:
            st.error(f"Could not process {file.name}: {exc}")
else:
    st.info("Upload a PDF or TXT document to extract structured fields.")
    st.code("Please process invoice INV-1042. Contact accounts@example.com. Amount due: ₹12,500. Date: 2026-10-22.", language="text")
st.caption("Prototype only. Extraction may miss or misread fields. Review outputs before downstream use.")
