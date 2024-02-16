import { bookmark } from "./Bookmark";

interface GetBookmarkResponse {
  bookmarks: bookmark[];
  last_bookmark_id: number;
}

export type { GetBookmarkResponse };
