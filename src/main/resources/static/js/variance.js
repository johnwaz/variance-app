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

function deletePage() {
    let page = $('#pageId').val();
    if (confirm(`Remove Page ${page}?`)) {
        document.getElementById("delete-page").submit();
    }
}