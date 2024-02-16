import { InputProps } from "./InputInterface";
import { Container } from "./InputStyle";

const Input = ({
  format = "line",
  label = "Name",
  width = "50%",
  ...props
}: InputProps) => {
  return (
    <Container className="form" width={width} format={format} {...props}>
      <input
        type="text"
        className="input"
        name={label}
        autoComplete="off"
        required
      />
      <label htmlFor={label} className="label">
        <span className="content">{label}</span>
      </label>
    </Container>
  );
};

export { Input };
