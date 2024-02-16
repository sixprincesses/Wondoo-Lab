import type { Meta, StoryObj } from "@storybook/react";

import { Button } from "./Button";

const meta = {
  title: "component/Button",
  component: Button,
  parameters: {
    layout: "centered",
  },
  tags: ["autodocs"],
  argTypes: {
    format: {
      control: "radio",
      options: ["line", "box"],
    },
    size: {
      control: "radio",
      options: ["small", "medium", "large"],
    },
    round: {
      control: "radio",
      options: ["block", "none", "round", "circle"],
    },
    anime: {
      control: "radio",
      options: ["none", "fade", "push", "float"],
    },
    label: {
      control: "text",
    },
    block: {
      control: "boolean",
    },
    disabled: {
      control: "boolean",
    },
  },
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Basic: Story = {
  args: {
    format: "line",
    size: "medium",
    round: "none",
    anime: "none",
    label: "Push",
    block: false,
    disabled: false,
  },
};
