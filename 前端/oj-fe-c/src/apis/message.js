import service from "@/utils/request";

export function getMessageListService(params) {
  return service({
    url: "/user/message/list",
    method: "get",
    params,
  });
}

export function delMessageService(textId) {
  return service({
    url: "/user/message/delete",
    method: "delete",
    params: { textId },
  });
}