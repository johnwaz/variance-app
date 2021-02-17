function deleteBook() {
    let book = $('#bookId').val();
    if (confirm(`Remove ${book}?`)) {
        document.getElementById("delete-book").submit();
    }
}

function deleteNovel() {
    let novel = $('#novelId').val();
    if (confirm(`Remove ${novel}?`)) {
        document.getElementById("delete-novel").submit();
    }
}

function deleteStory() {
    let story = $('#storyId').val();
    if (confirm(`Remove ${story}?`)) {
        document.getElementById("delete-story").submit();
    }
}

function deleteChapter() {
    let chapter = $('#chapterId').val();
    if (confirm(`Remove ${chapter}?`)) {
        document.getElementById("delete-chapter").submit();
    }
}

function deleteJournal() {
    let journal = $('#journalId').val();
    if (confirm(`Remove ${journal}?`)) {
        document.getElementById("delete-journal").submit();
    }
}

function deletePage() {
    let page = $('#pageId').val();
    if (confirm(`Remove Page ${page}?`)) {
        document.getElementById("delete-page").submit();
    }
}

$(document).ready(function () {
  $('textarea[data-limit-rows=true]')
    .on('keypress', function (event) {
        var textarea = $(this),
            text = textarea.val(),
            numberOfLines = (text.match(/\n/g) || []).length + 1,
            maxRows = parseInt(textarea.attr('rows'));

        if (event.which === 13 && numberOfLines === maxRows ) {
          return false;
        }
    });
});