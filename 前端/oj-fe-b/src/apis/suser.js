import service from "@/utils/request";
export function loginService(userAccount, password) {
    return service({
        url:"/sysUser/login",
        method:"post",
        data: {userAccount,password}
    })
} 
