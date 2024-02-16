import type { Meta, StoryObj } from "@storybook/react";
import InstanceMd from "./InstanceMd";

const meta: Meta<typeof InstanceMd> = {
  title: "Instance/Md",
  component: InstanceMd,
  parameters: {
    layout: "padded",
  },
  tags: ["autodocs"],
  argTypes: {
    previewStyle: {
      control: "select",
      options: ["tab", "vertical"],
      description: "미리보기 선택",
    },
    initialValue: {
      control: "text",
      description: "초기값 입력",
    },
  },
};

export default meta;

type Story = StoryObj<typeof InstanceMd>;

export const Tab: Story = {
  args: {
    previewStyle: "tab",
    initialValue: "tab화면입니다.",
  },
};

export const Vertical: Story = {
  args: {
    previewStyle: "vertical",
    initialValue: "vertical화면입니다.",
  },
};
