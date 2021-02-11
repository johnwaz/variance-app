function deleteBook() {
    let book = $('#bookId').val();
    if (confirm(`Remove ${book}?`)) {
        document.getElementById("delete-book").submit();
    }
}

function deleteChapter() {
    let chapter = $('#chapterId').val();
    if (confirm(`Remove ${chapter}?`)) {
        document.getElementById("delete-chapter").submit();
    }
}