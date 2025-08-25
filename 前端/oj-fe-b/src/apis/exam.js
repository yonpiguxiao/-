import service from '@/utils/request'

export function getExamListService(params) {
  return service({
    url: "/exam/list",
    method: "get",
    params,
  });
}

export function examAddService(params = {}) {
  return service({
    url: "/exam/add",
    method: "post",
    data: params,
  });
}

export function addExamQuestionService(params = {}) {
  return service({
    url: "/exam/question/add",
    method: "post",
    data: params,
  });
}

export function getExamDetailService(examId) {
  return service({
    url: "/exam/detail",
    method: "get",
    params: { examId },
  });
}

export function editExamService(params = {}) {
  return service({
    url: "/exam/edit",
    method: "put",
    data: params,
  });
}

export function delExamQuestionService(examId, questionId) {
  return service({
    url: "/exam/question/delete",
    method: "delete",
    params: { examId, questionId },
  });
}

export function delExamService(examId) {
  return service({
    url: "/exam/delete",
    method: "delete",
    params: { examId },
  });
}

export function publishExamService(examId) {
  return service({
    url: "/exam/publish",
    method: "put",
    params: { examId },
  });
}

export function cancelPublishExamService(examId) {
  return service({
    url: "/exam/cancelPublish",
    method: "put",
    params: { examId },
  });
}