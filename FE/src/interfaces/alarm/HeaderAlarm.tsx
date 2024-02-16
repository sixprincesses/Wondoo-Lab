interface EventObject {
    target_id: number,
    url: number,
    sub_url: number,
    content: string    
}

interface ContentObject {
    id: string,
    event: EventObject,
    type: string,
    read: boolean,
    time: number,
    unread_count: number,
    content?: string
}

interface MessageProps {
    member_id: number | null;
    setMessageNum: (newMessageNum: number) => void;
    showMessages: boolean;
    allMessages: ContentObject[];
    setAllMessages: React.Dispatch<React.SetStateAction<ContentObject[]>>;
}


export type { ContentObject, MessageProps };
