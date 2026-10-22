# Tekravio — Business Data Analytics Assistant

Tekravio is a local Streamlit app for exploring CSV business datasets with pandas. It profiles columns, summarizes numeric data, creates charts, and interprets a limited set of common natural-language questions using explicit, auditable rules rather than executing arbitrary generated code.

## Features
- Upload a CSV file and preview records.
- Inspect missing values, data types, descriptive statistics, and duplicate counts.
- Ask supported questions such as "average revenue", "sum of sales", "count by region", or "show revenue by category".
- Generate a chart when a grouping column and numeric metric are identified.
- Input validation and unit tests.

## Run locally
```bash
cd tekravio
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
streamlit run app.py
```
On Windows, activate with `.venv\\Scripts\\activate`.

## Supported question patterns
- `average <numeric column>`
- `sum of <numeric column>`
- `count by <categorical column>`
- `<numeric column> by <categorical column>`

Column names are matched case-insensitively. Questions outside these patterns return a clear guidance message instead of executing arbitrary code.

## Tests
```bash
pytest -q
```

## Stack
Python, pandas, NumPy, Streamlit, Plotly, pytest.
