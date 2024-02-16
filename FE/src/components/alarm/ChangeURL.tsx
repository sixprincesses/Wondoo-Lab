import { NavigateFunction } from "react-router-dom"

interface MessageEventObject {
    target_id: number;
    url: number;
    sub_url: number;
    content: string;
}

interface message {
    id: string;
    type: string;
    event: MessageEventObject;
    read: boolean;
    time: number;
    unread_count: number;
}

const ChangeURL = (message: message, navigate: NavigateFunction) => {
    if (message.type === 'follow') {
        navigate(`/member/${message.event.url}`)
    } else if (message.type === 'like') {
        navigate(`/detail/${message.event.url}`)
    } else if (message.type === 'comment') {
        navigate(`/detail/${message.event.url}`)
    } else if (message.type === 'comment_comment') {
        navigate(`/detail/${message.event.url}`)
    }
}

export default ChangeURL;