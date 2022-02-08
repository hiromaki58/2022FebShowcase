$(function() {
// 表示させる要素の総数をlengthメソッドで取得
var $numberListLen = $("#number_list li").length;
// デフォルトの表示数
var defaultNum = 6;
// ボタンクリックで追加表示させる数
var addNum = 6;
// 現在の表示数
var currentNum = 0;

$("#number_list").each(function() {
    // moreボタンを表示し、closeボタンを隠す
    $(this).find("#more_btn").show();
    $(this).find("#close_btn").hide();

    // defaultNumの数だけ要素を表示
    // defaultNumよりインデックスが大きい要素は隠す
    $(this).find("li:not(:lt("+defaultNum+"))").hide();

    // 初期表示ではデフォルト値が現在の表示数となる
    currentNum = defaultNum;

    // moreボタンがクリックされた時の処理
    $("#more_btn").click(function() {
    // 現在の表示数に追加表示数を加えていく
    currentNum += addNum;

    // 現在の表示数に追加表示数を加えた数の要素を表示する
    $(this).parent().find("li:lt("+currentNum+")").slideDown();

    // 表示数の総数よりcurrentNumが多い=全て表示された時の処理
    if($numberListLen <= currentNum) {
        // 現在の表示数をデフォルト表示数へ戻す
        currentNum = defaultNum;
        // インデックス用の値をセット
        indexNum = currentNum - 1;

        // moreボタンを隠し、closeボタンを表示する
        $("#more_btn").hide();
        $("#close_btn").show();

        // closeボタンがクリックされた時の処理
        $("#close_btn").click(function() {
        // デフォルト数以降=インデックスがindexNumより多い要素は非表示にする
        $(this).parent().find("li:gt("+indexNum+")").slideUp();

        // closeボタンを隠し、moreボタンを表示する
        $(this).hide();
        $("#more_btn").show();
        });
    }
    });
});
});