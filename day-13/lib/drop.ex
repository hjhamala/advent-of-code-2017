defmodule Drop do
  @moduledoc """
  Documentation for Drop.
  """

  def coerce([layer, range]) do
    {String.to_integer(layer), String.trim(range) |> String.to_integer}
  end


  def parse do
    File.stream!("input.txt")
    |> Stream.map(fn x -> String.split(x, ":") end)
    |> Stream.map(&coerce/1)
    |> Enum.to_list
  end

  def triangle_wave(range,time) do
    range - abs(rem(time, (2 * range)) - range)
  end

  def determine_location(range, time) do
    triangle_wave(range-1, time)
  end

  def calculate_severity({layer, range}) do
    scanner_location = determine_location(range,layer)
    case scanner_location do
      0 -> layer * range
      _ -> 0
    end
  end

  def calculate_severity({layer, range}, delay) do
    calculate_severity({layer+delay,range})
  end


  def example do
    [{0, 3}, {1, 2}, {4, 4}, {6, 4}]
  end

  def solve_part_one do
    parse()
    |> Enum.map(&calculate_severity/1)
    |> Enum.reduce(fn x, y -> x + y end)
  end

  def calculate_with_delay(input, delay) do
    Enum.map(input, fn x -> calculate_severity(x, delay) end )
    |> Enum.reduce(fn x, y -> x + y end)
  end

  def try_with_delay(input, delay) do
    case calculate_with_delay(input, delay) do
      0 -> delay
      _ -> try_with_delay(input,delay + 1)
    end
  end

  def solve_part_two do
    try_with_delay(parse(),0)

  end



end
