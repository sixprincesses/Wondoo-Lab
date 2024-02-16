import { useNavigate } from "react-router-dom";
import basicProfile from "../../assets/icon/basicProfile.png";
import { member } from "../../interfaces/feed/FeedState";
import {
  Keyword,
  Keywords,
  Member,
  Members,
  NoData,
  Wrapper,
} from "./SearchResultStyle";

interface SearchResultProps {
  title: "member" | "keyword";
  values: any[];
}

const SearchResult = ({ title, values }: SearchResultProps) => {
  const navigate = useNavigate();

  if (title === "member") {
    return (
      <Wrapper>
        <h3>멤버 검색 결과</h3>
        <div />
        <Members>
          {values?.length === 0 ? (
            <NoData>검색 결과가 없습니다.</NoData>
          ) : (
            values.map((value: member) => (
              <Member
                key={value.member_id}
                onMouseDown={() => navigate(`/member/${value.member_id}`)}
              >
                <img
                  src={value.image_id ? `https://testmediaserver.s3.ap-southeast-2.amazonaws.com/${value.image_id}`: basicProfile}
                  alt="프로필 사진"
                />
                <p>{value.nickname}</p>
              </Member>
            ))
          )}
        </Members>
      </Wrapper>
    );
  } else if (title === "keyword") {
    return (
      <Wrapper>
        <h3>키워드 검색 결과</h3>
        <div />
        <Keywords>
          {values?.length === 0 ? (
            <NoData>검색 결과가 없습니다.</NoData>
          ) : (
            values.map((value: string, idx) => (
              <Keyword key={idx}>
                #{" "}
                <span onClick={() => navigate(`/search/${value}`)}>
                  {value}
                </span>
              </Keyword>
            ))
          )}
        </Keywords>
      </Wrapper>
    );
  }
};

export default SearchResult;
