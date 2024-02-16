interface EventObject {
    target_id: number,
    url: number,
    sub_url: number;
    content: string    
}

interface MessageObject {
    id: string,
    event: EventObject,
    type: string,
    read: boolean,
    time: number,
    unread_count: number,
    content?: string
}

const createMessage = (message: MessageObject): MessageObject => {
    let content = '';
    if (message.type === 'follow') {
        content = `${message.event.content} 님이 당신을 팔로우했습니다`;
    } else if (message.type === 'comment' ) {
        content = `${message.event.content} 님이 댓글을 등록했습니다`
    } else if (message.type === 'like') {
        content = `${message.event.content} 님이 좋아요를 눌렀습니다`
    } else if (message.type === 'comment_comment') {
        content = `${message.event.content} 님이 답글을 등록했습니다`
    }
    
    
    const newMessage: MessageObject = {
        ...message,
        content: content
    };

    console.log(content, newMessage)
    
    return newMessage;
}

export default createMessage;