import axios from 'axios'

//不同的功能 通过axios请求不同的接口地址
const service = axios.create({
    baseURL:"/dev-api",
    timeout:5000,
})

export default service