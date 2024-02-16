import { Meta, StoryObj } from "@storybook/react";
import { Select } from "./Select";

const meta = {
  title: "component/Select",
  component: Select,
  tags: ["autodocs"],
} satisfies Meta<typeof Select>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Basic: Story = {
  args: {},
};
