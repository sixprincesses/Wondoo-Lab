import { debounce } from "lodash";
import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import useSearchKeywords from "../../apis/keyword/useSearchKeywords";
import useSearchMembers from "../../apis/member/useSearchMember";
import search from "../../assets/icon/search.png";
import { member } from "../../interfaces/feed/FeedState";
import SearchResult from "./SearchResult";
import {
  Data,
  Icon,
  Input,
  Label,
  NoData,
  SearchResultBox,
  Wrapper,
} from "./SearchStyle";

interface SearchResultState {
  members: member[];
  keywords: string[];
}

const Search = () => {
  const location = useLocation();
  const searchMembers = useSearchMembers();
  const searchKeywords = useSearchKeywords();
  const [input, setInput] = useState<string>("");
  const [results, setResults] = useState<SearchResultState>({
    members: [],
    keywords: [],
  });
  const [isFocus, setIsFocus] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const resultRef = useRef<HTMLDivElement>(null);

  // 검색 로직
  const [isResult, setIsResult] = useState({
    members: false,
    keywords: false,
  });
  const MemberSearch = async (keyword: string) => {
    const members = await searchMembers({ keyword });
    setIsResult((prev) => ({ ...prev, members: true }));
    if (!members) {
      console.log("멤버가 없습니다.");
      return;
    }
    setResults((prev) => ({ ...prev, members: members }));
  };
  const KeywordSearch = async (keyword: string) => {
    const keywords = await searchKeywords({ keyword });
    setIsResult((prev) => ({ ...prev, keywords: true }));
    if (!keywords) {
      console.log("키워드가 없습니다.");
      return;
    }
    setResults((prev) => ({ ...prev, keywords: keywords }));
  };
  const debounceSearch = debounce((keyword: string) => {
    if (!keyword) return;
    MemberSearch(keyword);
    KeywordSearch(keyword);
  }, 1000);
  const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setInput(value);
    setResults({
      members: [],
      keywords: [],
    });
    setIsResult({
      members: false,
      keywords: false,
    });
    debounceSearch(value);
  };

  // 검색창 열기/닫기 로직
  const handleWindowClick = (e: MouseEvent) => {
    const target = e.target as HTMLDivElement | HTMLInputElement;
    if (inputRef.current?.contains(target)) return;
    if (resultRef.current?.contains(target)) return;
    setInput("");
    setIsFocus(false);
    window.removeEventListener("mouseup", handleWindowClick);
  };
  const handleFocus = () => {
    setIsFocus(true);
    window.addEventListener("mouseup", handleWindowClick);
  };
  useEffect(() => {
    if (!isFocus) return;
    setInput("");
    setIsFocus(false);
    window.removeEventListener("mouseup", handleWindowClick);
  }, [location]);

  return (
    <Wrapper>
      <Input
        id="search"
        placeholder="검색어를 입력하세요"
        ref={inputRef}
        value={input}
        onChange={handleInput}
        onFocus={handleFocus}
        isFocus={isFocus}
      />
      <Label htmlFor="search">
        <Icon src={search} alt="검색하기" />
      </Label>
      <SearchResultBox ref={resultRef} isFocus={isFocus}>
        {!input ? (
          <NoData>검색어를 입력하세요</NoData>
        ) : !isResult.members && !isResult.keywords ? (
          <NoData>Loading</NoData>
        ) : (
          <Data>
            {isResult.members ? (
              <SearchResult title="member" values={results.members} />
            ) : null}
            {isResult.keywords ? (
              <SearchResult title="keyword" values={results.keywords} />
            ) : null}
          </Data>
        )}
      </SearchResultBox>
    </Wrapper>
  );
};

export default Search;
