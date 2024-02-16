import { Meta, StoryObj } from "@storybook/react";
import { CardFile } from "./CardFile";

const meta = {
  title: "component/card/File",
  component: CardFile,
  tags: ["autodocs"],
} satisfies Meta<typeof CardFile>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Basic: Story = {
  args: {},
};
