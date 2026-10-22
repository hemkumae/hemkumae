from app import chunk_text, retrieve

def test_chunk_text_empty_and_content():
    assert chunk_text("") == []
    assert "financial" in chunk_text("financial education")[0]

def test_retrieve_ranks_relevant_passage():
    docs = {
        "loan.txt": "Compare loan interest rates, repayment schedules, fees, and total repayment.",
        "garden.txt": "Plant seeds in well-drained soil and water the garden."
    }
    hits = retrieve("How do I compare loan fees and repayment?", docs)
    assert hits
    assert hits[0]["source"] == "loan.txt"

def test_blank_question_returns_no_hits():
    assert retrieve("  ", {"a.txt": "some text"}) == []
