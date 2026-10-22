from app import classify_request, extract_record

def test_invoice_classification_and_fields():
    text = "Invoice INV-1042. Contact accounts@example.com. Amount due ₹12,500 on 2026-10-22."
    record = extract_record(text, "invoice.txt")
    assert record["request_type"] == "invoice_or_payment"
    assert record["email"] == "accounts@example.com"
    assert "INV-1042" in record["reference_ids"]
    assert record["amounts"]

def test_support_classification():
    assert classify_request("The app is not working and I need support") == "support_request"

def test_empty_document_needs_review():
    record = extract_record("   ")
    assert record["needs_human_review"] is True

def test_general_fallback():
    assert classify_request("Hello, please see attached.") == "general_request"
