interface message {
  member_id: number;
  reference: string;
  image_id: string;
  nickname: string;
  content: string;
  message_type: string;
  createdTime: Date;
}

interface messages {
  messages: message[];
}

interface body {
  status: number;
  message: string;
  response: messages;
}

interface messageReceivePayload {
  headers: object;
  body: body;
  statusCode: string;
  statusCodeValue: number;
}

export type { message, messageReceivePayload };
