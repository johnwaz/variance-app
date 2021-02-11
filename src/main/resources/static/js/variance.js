function deleteBook() {
    let book = $('#bookId').val();
    if (confirm(`Remove ${book}?`)) {
        document.getElementById("delete-book").submit();
    }
}