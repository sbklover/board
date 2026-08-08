// 어디서 수정 화면으로 들어왔는지에 따라 취소 버튼 목적지를 분기
// - view.html에서 들어온 경우 -> view.html로 복귀
// - list.html(목록)에서 들어온 경우 -> list.html로 복귀
document.getElementById('btnCancel').addEventListener('click', function () {
    var boardNo = this.dataset.no;
    var ref = document.referrer;

    if (ref && ref.indexOf('/board/view/') !== -1 && boardNo) {
        location.href = '/board/view/' + boardNo;
    } else {
        location.href = '/board';
    }
});