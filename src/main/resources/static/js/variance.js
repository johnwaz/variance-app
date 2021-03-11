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
    if (confirm(`Remove Chapter ${chapter}?`)) {
        document.getElementById("delete-chapter").submit();
    }
}

function deleteJournal() {
    let journal = $('#journalId').val();
    if (confirm(`Remove ${journal}?`)) {
        document.getElementById("delete-journal").submit();
    }
}

function deleteNotebook() {
    let notebook = $('#notebookId').val();
    if (confirm(`Remove ${notebook}?`)) {
        document.getElementById("delete-notebook").submit();
    }
}

function deleteSubject() {
    let subject = $('#subjectId').val();
    if (confirm(`Remove ${subject}?`)) {
        document.getElementById("delete-subject").submit();
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

function countJPChars(obj){
    var maxLength = 1000;
    var strLength = obj.value.length;
    var charRemain = (maxLength - strLength);

    if (charRemain <= 0) {
        document.getElementById("jp-charNum").innerHTML = '<span style="color: red;">You have reached the limit of '+maxLength+' characters</span>';
    } else {
        document.getElementById("jp-charNum").innerHTML = charRemain+' characters remaining';
    }
}

function countNBSPChars(obj){
    var maxLength = 1500;
    var strLength = obj.value.length;
    var charRemain = (maxLength - strLength);

    if (charRemain <= 0) {
        document.getElementById("nbsp-charNum").innerHTML = '<span style="color: red;">You have reached the limit of '+maxLength+' characters</span>';
    } else {
        document.getElementById("nbsp-charNum").innerHTML = charRemain+' characters remaining';
    }
}

function countBCPChars(obj){
    var maxLength = 2400;
    var strLength = obj.value.length;
    var charRemain = (maxLength - strLength);

    if (charRemain <= 0) {
        document.getElementById("bcp-charNum").innerHTML = '<span style="color: red;">You have reached the limit of '+maxLength+' characters</span>';
    } else {
        document.getElementById("bcp-charNum").innerHTML = charRemain+' characters remaining';
    }
}