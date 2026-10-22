import re
import pandas as pd
import plotly.express as px
import streamlit as st

def find_column(columns, phrase):
    phrase = phrase.strip().lower().strip(" '\"")
    exact = [c for c in columns if str(c).lower() == phrase]
    if exact:
        return exact[0]
    matches = [c for c in columns if phrase and phrase in str(c).lower()]
    return matches[0] if len(matches) == 1 else None

def answer_question(df, question):
    q = question.strip().lower()
    numeric = list(df.select_dtypes(include="number").columns)
    if not q:
        return {"message": "Enter a question to begin."}
    avg = re.search(r"(?:average|mean)\s+(?:of\s+)?(.+)$", q)
    total = re.search(r"(?:sum|total)\s+(?:of\s+)?(.+)$", q)
    count = re.search(r"count\s+by\s+(.+)$", q)
    grouped = re.search(r"(.+?)\s+by\s+(.+)$", q)
    if avg:
        col = find_column(numeric, avg.group(1))
        if col is None:
            return {"message": f"Could not identify one numeric column. Numeric columns: {', '.join(map(str, numeric)) or 'none'}."}
        return {"message": f"Average {col}: {df[col].mean():,.4g}", "value": float(df[col].mean())}
    if total:
        col = find_column(numeric, total.group(1))
        if col is None:
            return {"message": f"Could not identify one numeric column. Numeric columns: {', '.join(map(str, numeric)) or 'none'}."}
        return {"message": f"Sum of {col}: {df[col].sum():,.4g}", "value": float(df[col].sum())}
    if count:
        col = find_column(list(df.columns), count.group(1))
        if col is None:
            return {"message": f"Could not identify a column. Available columns: {', '.join(map(str, df.columns))}."}
        return {"message": f"Record count by {col}", "table": df[col].fillna("(missing)").value_counts().rename_axis(col).reset_index(name="count")}
    if grouped:
        metric = find_column(numeric, grouped.group(1))
        category = find_column(list(df.columns), grouped.group(2))
        if metric and category:
            table = df.groupby(category, dropna=False)[metric].sum().reset_index().sort_values(metric, ascending=False)
            return {"message": f"Sum of {metric} by {category}", "table": table, "chart": (category, metric)}
    return {"message": "I don't recognize that pattern yet. Try 'average revenue', 'sum of sales', 'count by region', or 'revenue by category'."}

st.set_page_config(page_title="Tekravio", layout="wide")
st.title("Tekravio")
st.caption("Business data exploration and natural-language analytics")
file = st.file_uploader("Upload a business dataset (CSV)", type=["csv"])
if file:
    try:
        df = pd.read_csv(file)
        if df.empty or len(df.columns) == 0:
            st.error("The CSV has no data or columns.")
            st.stop()
    except Exception as exc:
        st.error(f"Could not read CSV: {exc}")
        st.stop()
    st.subheader("Dataset preview")
    st.dataframe(df.head(100), use_container_width=True)
    a, b, c = st.columns(3)
    a.metric("Rows", f"{len(df):,}")
    b.metric("Columns", len(df.columns))
    c.metric("Duplicate rows", int(df.duplicated().sum()))
    with st.expander("Data quality and profile"):
        st.dataframe(pd.DataFrame({"dtype": df.dtypes.astype(str), "missing": df.isna().sum(), "unique": df.nunique(dropna=False)}), use_container_width=True)
        numeric = df.select_dtypes(include="number")
        if not numeric.empty:
            st.dataframe(numeric.describe().T, use_container_width=True)
    question = st.text_input("Ask a data question", placeholder="average revenue")
    if st.button("Analyze", type="primary", disabled=not question.strip()):
        result = answer_question(df, question)
        st.write(result["message"])
        if "table" in result:
            st.dataframe(result["table"], use_container_width=True)
        if "chart" in result:
            category, metric = result["chart"]
            st.plotly_chart(px.bar(result["table"], x=category, y=metric, title=result["message"]), use_container_width=True)
else:
    st.info("Upload a CSV to explore its data. No file is uploaded to a remote service by this app.")
    st.code("region,revenue\nSouth,12000\nNorth,9500\nSouth,8000\nWest,11000", language="csv")
