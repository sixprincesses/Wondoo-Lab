import { Meta, StoryObj } from "@storybook/react";
import { InstanceGithub } from "./InstanceGithub";

const meta = {
  title: "Instance/Github",
  component: InstanceGithub,
  parameters: {
    layout: "padded",
  },
  tags: ["autodocs"],
} satisfies Meta<typeof InstanceGithub>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Basic: Story = {
  args: {
    userName: "KTaeGyu",
  },
};
