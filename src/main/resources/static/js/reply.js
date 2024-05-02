async function get1(bno){       //async -> 비동기 처리할 함수 명시
    //test를 위해 만듦
    const result = await axios.get(`/replies/list/${bno}`);      //await -> 비동기 호출
    // console.log(result);
    return result.data;
}
async function getList({bno, page, size, goLast}){
    //golist는 작성시 강제적으로 마지막 페이지를 주기 위해서
    const result = await axios.get(`/replies/list/${bno}`, {params : {page, size}});

    if(goLast){
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size}) //key: value 값
    }
    return result.data;

}

async  function addReply(replyObj){
    const response = await axios.post(`/replies/`, replyObj)
    return response.data
}