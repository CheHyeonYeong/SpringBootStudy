async function get1(bno){       //async -> 비동기 처리할 함수 명시
    //test를 위해 만듦
    const result = await axios.get(`/replies/list/${bno}`);      //await -> 비동기 호출
    // console.log(result);
    return result.data;
}
async function getList({bno, page, size, goLast}){
    //golist는 작성시 강제적으로 마지막 페이지를 주기 위해서
    const result = await axios.get(`/replies/list/${bno}`, {param: {page, size}});
    return result.data;

}

