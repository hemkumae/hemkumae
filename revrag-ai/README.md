# RevRAG AI — Banking & Financial Knowledge Assistant

RevRAG AI is a retrieval-augmented question-answering demo for a small, explicitly provided collection of financial education documents. It retrieves relevant passages and returns an answer grounded in those passages, with source names. It is a portfolio prototype, not a bank product or financial adviser.

## Features
- Upload TXT or PDF knowledge files, or start with the included sample content.
- Split documents into overlapping chunks and retrieve relevant passages with TF-IDF.
- Show source passages so users can inspect the evidence.
- Optional OpenAI answer generation when `OPENAI_API_KEY` is configured; otherwise the app returns a transparent extractive answer.
- Basic validation and retrieval tests.

## Run locally
```bash
cd revrag-ai
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
streamlit run app.py
```
On Windows, activate with `.venv\\Scripts\\activate`.

Optional LLM mode: set `OPENAI_API_KEY` in your shell and install the requirements. Never commit API keys. The retrieval pipeline works without an API key.

## Scope and limitations
The sample content is illustrative, not official bank policy. Retrieval quality depends on the documents supplied. Do not enter account numbers, credentials, or other sensitive data. Answers are informational and should not be treated as financial advice.

## Tests
```bash
pytest -q
```

## Stack
Python, Streamlit, scikit-learn TF-IDF retrieval, PyPDF, pytest, optional OpenAI API.
