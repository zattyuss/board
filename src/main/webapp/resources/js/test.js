console.log("뚱띵이");

(function myFunction(){
	console.log("뚱땽이 실행");
})();

// 삭제

var obj = {
	"bno" : "1",
	"reply" : "알겠슘당",
	"replyer" : "슘당이",
	
	add : function(){
		console.log("??????????");
	}
}
console.log(obj);

//객체 접근
console.log(obj.bno);
console.log(obj.reply);
console.log(obj.replyer);

// 객체를 함수로 변환
function myObjFun(bno, reply, replyer){
	return {
		"bno" : bno,
		"reply" : reply,
		"replyer" : replyer
	};
}

let reply1 =  myObjFun(10, "뚱땅이", "누군가");
console.log(reply1);










