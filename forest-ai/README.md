# Forest AI — Document Processing Agent

Forest AI is a document intake prototype that extracts text from PDF/TXT files, classifies the likely request type, and produces a validated JSON record with key fields and a review flag. It is intentionally designed to be inspectable and safe: extraction rules are deterministic and it does not send documents to an external model by default.

## Features
- Upload PDF and TXT documents.
- Extract common fields such as email, phone, dates, reference IDs, and monetary amounts.
- Classify common business requests: invoice/payment, support issue, application, or general request.
- Produce JSON and flag low-confidence or incomplete records for human review.
- Download structured results and run tests.

## Run locally
```bash
cd forest-ai
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
streamlit run app.py
```
On Windows, activate with `.venv\\Scripts\\activate`.

## Output example
```json
{
  "document_name": "sample.txt",
  "request_type": "invoice_or_payment",
  "email": "accounts@example.com",
  "phone": null,
  "dates": ["2026-10-22"],
  "amounts": ["₹12,500"],
  "reference_ids": ["INV-1042"],
  "needs_human_review": false
}
```
Actual output depends on the document. Extraction is pattern-based, not guaranteed to be complete; always review before using results in a business workflow.

## Tests
```bash
pytest -q
```

## Stack
Python, Streamlit, PyPDF, regular expressions, JSON, pytest.
