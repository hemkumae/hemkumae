import pandas as pd
from app import answer_question, find_column

def sample():
    return pd.DataFrame({"Region": ["South", "North", "South"], "Revenue": [10, 20, 30]})

def test_average_numeric_column():
    result = answer_question(sample(), "average revenue")
    assert result["value"] == 20

def test_grouped_sum():
    result = answer_question(sample(), "revenue by region")
    assert result["table"].set_index("Region").loc["South", "Revenue"] == 40

def test_unknown_question_is_safe():
    assert "Try" in answer_question(sample(), "delete all data")["message"]

def test_ambiguous_column_not_guessed():
    assert find_column(["sales_total", "sales_monthly"], "sales") is None
