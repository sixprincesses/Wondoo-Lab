import { Meta, StoryObj } from "@storybook/react";
import { Input } from "./Input";

const meta = {
  title: "Component/Input",
  component: Input,
  parameters: {
    layout: "padded",
  },
  tags: ["autodocs"],
  argTypes: {
    format: {
      control: "select",
      options: ["line", "box"],
      description: "입력창의 종류를 선택",
      defaultValue: "line",
    },
    width: {
      control: {
        step: 10,
      },
      description: "입력창의 넓이 % 를 선택",
      defaultValue: 50,
    },
    label: {
      defaultValue: "Title",
    },
  },
} satisfies Meta<typeof Input>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Line: Story = {
  args: {
    format: "line",
    width: "50",
    label: "Name",
  },
};

export const Box: Story = {
  args: {
    format: "box",
    width: "50",
    label: "Name",
  },
};
