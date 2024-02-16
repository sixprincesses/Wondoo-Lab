import { message } from "./MessageReceivePayload";

interface channel {
  name: string;
  reference: string;
  image_id: string;
  last_message: message | null;
}

interface response {
  channels: channel[];
}

interface body {
  status: number;
  message: string;
  response: response;
}

interface channelReceivePayload {
  headers: object;
  body: body;
  statusCode: string;
  statusCodeValue: number;
}

export type { channel, channelReceivePayload };
