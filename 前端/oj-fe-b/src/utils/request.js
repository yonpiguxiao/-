import axios from 'axios'

//不同的功能 通过axios请求不同的接口地址
const service = axios.create({
    baseURL: "/dev-api",
    timeout: 5000,
})

service.interceptors.response.use(
    (res) => {
        // 未设置状态码则默认成功状态
        const code = res.data.code;
        const msg = res.data.msg;
        if (code !== 1000) {
            ElMessage.error(msg);
            return Promise.reject(new Error(msg));
        } else {
            return Promise.resolve(res.data);
        }
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default service