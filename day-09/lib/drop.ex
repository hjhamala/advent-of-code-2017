defmodule Drop do
  @moduledoc """
  Documentation for Drop.
  """

  def parse do
    File.stream!("input.txt")
    |> Enum.to_list
    |> List.first
  end

  def process([head|tail], acc, garbagemode?) do
    case [garbagemode?, head] do
      [false, "<"] -> process(tail, acc, true)
      [true, "!"] ->  [_| new_tail] = tail
                      process(new_tail, acc, true)
      [true, ">"] -> process(tail, acc, false)
      [true, _] ->  process(tail, acc, true)
      [false, _] ->  process(tail, acc ++ [head], false)
    end
  end

  def process([], acc, _) do
    acc
  end

  def score([head|tail], acc, scoremode) do
    case head do
      "{" -> score(tail, acc+scoremode, scoremode+1)
      "}" -> score(tail, acc, scoremode-1)
      _ -> score(tail, acc, scoremode)
    end
  end

  def score([], acc, _) do
    acc
  end

  def solve_part_1() do
    char_list = parse()
                |> String.graphemes
    processed = process(char_list, [], false)
    score(processed, 0, 1)
  end

  def garbage_collector([head|tail], acc, garbagemode?) do
    case [garbagemode?, head] do
      [false, "<"] -> garbage_collector(tail, acc, true)
      [true, "!"] ->  [_| new_tail] = tail
                      garbage_collector(new_tail, acc, true)
      [true, ">"] -> garbage_collector(tail, acc, false)
      [true, _] ->  garbage_collector(tail, acc + 1, true)
      [false, _] ->  garbage_collector(tail, acc, false)
    end
  end

  def garbage_collector([], acc, _) do
    acc
  end

  def solve_part_2() do
    char_list = parse()
                |> String.graphemes
    garbage_collector(char_list, 0, false)
  end

end
