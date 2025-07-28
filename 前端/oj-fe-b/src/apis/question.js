import service from "@/utils/request";

export function getQuestionListService(params) {
    return service({
        url: "/question/list",
        method: "get",
        params,
    });
}