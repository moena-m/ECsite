//let login = (event) => { }; の部分は、旧文法で記載すると、
//let login = function(event) { }; と同じ。
let login = (event) => {
	event.preventDefault();
	//変数jsonStringにinputで入れた値格納
	let jsonString = {
			'userName': $('input[name=userName]').val(),
			'password': $('input[name=password]').val()
	};
// jqueryのajaxメソッドを呼び出し,IndexControllerに追加したloginメソッドにアクセス
	$.ajax({
		type:'POST',// 使用するHTTPメソッド
		url:'/ecsite/api/login',// 通信先
		data: JSON.stringify(jsonString),
		contentType: 'application/json',
		datatype:'json',
		scriptCharaset: 'utf-8'
	})
	
// thenメソッドのコールバック関数により 参照した結果を画面に出力
	.then((result) => {
		let user = JSON.parse(result);//JSONを解析する、JSONを読み込む
		$('#welcome').text(` - - ようこそ！ ${user.fullName} さん`);
		$('#hiddenUserId').val(user.id);.// val()を使うとvalue属性の値を取得したり設置することができる
		$('input[name=userName]').val('');
		$('input[name=password]').val('');
	}, ()=> {
		console.error('Error: ajax connection failed.');
		}
	);
};



// 「カートに入れる」機能↓

let addCart = (event) => {
	let tdList = $(event.target).parent().parent().find('td');// 「カートに入れる」ボタンをクリックし、index.htmlのtdから要素を取得して格納
	
	let id = $(tdList[0]).text();　// .textメソッドで指定した要素の取得
	let goodsName = $(tdList[1]).text();
	let price = $(tdList[2]).text();
	let count = $(tdList[3]).find('input').val(); // id = a の値
	
	if(count === '0' || count === '') {
		alert('注文数が0または空欄です。');
		return;
	}
	
	let cart = {　// cartという変数に取得したid,goodsName,password格納
			'id': id,
			'goodsName': goodsName,
			'price':price,
			'count':count
	};
	cartList.push(cart);　// index.htmlのcartListにcartリストを入れる
	
	// tbodyに入れる
	let tbody =$('#cart').find('tbody');
	$(tbody).children().remove();
	cartList.forEach(function(cart, index) {
		let tr = $('<tr />');
		
		$('<td />',{ 'text': cart.id}).appendTo(tr);
		$('<td />',{ 'text': cart.goodsName }).appendTo(tr);
		$('<td />',{ 'text': cart.price }).appendTo(tr);
		$('<td />',{ 'text': cart.count }).appendTo(tr);
		let tdButton = $('<td />');
		$('<button />', {
			'text':'カート削除',
			'class':'removeBtn',
		}).appendTo(tdButton);
		
		$(tdButton).appendTo(tr);
		$(tr).appendTo(tbody);// trをtbodyに入れる
});
$('.removeBtn').on('click',removeCart);
};

// 購入処理↓
let buy = (event) => {
	$.ajax({
		type:'POST',
		url:'/ecsite/api/purchase',//購入
		data: JSON.stringify({
			"userId":$('#hiddenUserId').val(),
			"cartList": cartList
		}),
		contentType: 'application/json',
		datatype:'json',
		scriptCharaset: 'utf-8'
	})
	.then((result) => {
		alert('購入しました。');
	},() => {
		console.error('Error: ajax connection failed.');
		}
	);
};

// カート内の削除機能
let removeCart = (event) => {
	const tdList = $(event.target).parent().parent().find('td');
	let id = $(tdList[0]).text();
	cartList = cartList.filter(function(cart){
		return cart.id !==id;
	});
	$(event.target).parent().parent().remove();
};

// showHistory関数
let showHistory = () => {
	$.ajax({
		type:'POST',
		url:'/ecsite/api/history',
		data: JSON.stringify({"userId": $('#hiddenUserId').val() }),
		contentType: 'application/json',
		datatype:'json',
		scriptCharaset: 'utf-8'
})
	.then((result) => {
			let historyList = JSON.parse(result);
			let tbody = $('#historyTable').find('tbody');
			$(tbody).children().remove();
			historyList.forEach((history,index) => {
				let tr = $('<tr />');
			
				$('<td />',{ 'text': history.goodsName }).appendTo(tr);
				$('<td />',{ 'text': history.itemCount }).appendTo(tr);
				$('<td />',{ 'text': history.createdAt }).appendTo(tr);
				
				$(tr).appendTo(tbody);
			});
				$("#history").dialog("open");
		}, () => {
			console.error('Error: ajax connection failed.');
		}
	);
}