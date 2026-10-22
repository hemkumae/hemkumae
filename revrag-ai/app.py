from pathlib import Path
import os
import re
import streamlit as st
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

SAMPLE_DOCS = {
    "savings_basics.txt": """A savings account is designed to hold money securely while allowing access to deposits. Interest rates, minimum balance rules, fees, and withdrawal limits vary by institution and account type. Review the current account terms before opening an account.""",
    "budgeting_basics.txt": """A budget compares income with expenses over a period. Common categories include housing, food, transport, utilities, savings, and debt payments. Review spending regularly and keep an emergency fund appropriate to your circumstances.""",
    "loan_basics.txt": """Before accepting a loan, compare the annual percentage rate, total repayment amount, fees, repayment schedule, late-payment terms, and whether the rate is fixed or variable. Read the lender's official agreement and ask questions about terms you do not understand."""
}

def chunk_text(text, chunk_size=700, overlap=120):
    text = re.sub(r"\s+", " ", text).strip()
    if not text:
        return []
    chunks = []
    start = 0
    while start < len(text):
        end = min(len(text), start + chunk_size)
        chunks.append(text[start:end])
        if end == len(text):
            break
        start = max(start + 1, end - overlap)
    return chunks

def retrieve(question, documents, top_k=3):
    passages, sources = [], []
    for source, text in documents.items():
        for chunk in chunk_text(text):
            passages.append(chunk)
            sources.append(source)
    if not passages or not question.strip():
        return []
    vectorizer = TfidfVectorizer(stop_words="english")
    matrix = vectorizer.fit_transform(passages + [question])
    scores = cosine_similarity(matrix[-1], matrix[:-1]).ravel()
    ranked = scores.argsort()[::-1][:top_k]
    return [{"text": passages[i], "source": sources[i], "score": float(scores[i])} for i in ranked if scores[i] > 0]

def extract_documents(uploaded_files):
    docs = {}
    for uploaded in uploaded_files:
        name = uploaded.name
        raw = uploaded.getvalue()
        if name.lower().endswith(".pdf"):
            from pypdf import PdfReader
            import io
            reader = PdfReader(io.BytesIO(raw))
            text = "\n".join(page.extract_text() or "" for page in reader.pages)
        elif name.lower().endswith(".txt"):
            text = raw.decode("utf-8", errors="replace")
        else:
            continue
        if text.strip():
            docs[name] = text
    return docs

def answer_question(question, hits):
    if not hits:
        return "I couldn't find relevant information in the provided documents. Try rephrasing the question or adding a suitable source document."
    context = "\n\n".join(f"[{h['source']}] {h['text']}" for h in hits)
    key = os.getenv("OPENAI_API_KEY")
    if key:
        try:
            from openai import OpenAI
            client = OpenAI(api_key=key)
            response = client.chat.completions.create(
                model=os.getenv("OPENAI_MODEL", "gpt-4o-mini"),
                temperature=0,
                messages=[
                    {"role": "system", "content": "Answer only from the supplied context. If it does not contain the answer, say so. This is general financial education, not personalized financial advice. Cite source filenames in brackets."},
                    {"role": "user", "content": f"Question: {question}\n\nContext:\n{context}"}
                ]
            )
            return response.choices[0].message.content or context
        except Exception as exc:
            return f"LLM generation unavailable ({type(exc).__name__}); showing retrieved evidence instead.\n\n{context}"
    return "Here are the most relevant passages from your documents:\n\n" + "\n\n".join(
        f"**{h['source']}** (match {h['score']:.2f})\n{h['text']}" for h in hits
    )

st.set_page_config(page_title="RevRAG AI", page_icon="📚", layout="wide")
st.title("RevRAG AI")
st.caption("A source-grounded financial knowledge assistant — portfolio prototype")
uploads = st.file_uploader("Add knowledge documents (PDF or TXT)", type=["pdf", "txt"], accept_multiple_files=True)
docs = dict(SAMPLE_DOCS)
if uploads:
    docs.update(extract_documents(uploads))
with st.sidebar:
    st.subheader("Knowledge base")
    st.write(f"{len(docs)} document(s) available")
    st.write("Sample documents are illustrative. Uploaded documents are processed in this session.")
    st.warning("Do not upload account credentials or sensitive personal data.")
question = st.text_input("Ask a question about the available documents", placeholder="What should I compare before taking a loan?")
if st.button("Find answer", type="primary", disabled=not question.strip()):
    hits = retrieve(question, docs)
    st.subheader("Answer")
    st.write(answer_question(question, hits))
    if hits:
        with st.expander("Inspect retrieved evidence"):
            for hit in hits:
                st.markdown(f"**{hit['source']} · relevance {hit['score']:.3f}**")
                st.write(hit["text"])
                st.divider()
st.caption("Educational demo only. Confirm financial decisions against official documents and qualified professionals.")
