defmodule Drop do
  @moduledoc """
  Documentation for Drop.
  """

  def coerce(coll) do
    [target, operation, value, _ , condition_variable, condition, condition_value] = coll
    [target, operation, String.to_integer(value), condition_variable, condition, String.to_integer(condition_value)]
  end


  def parse do
    File.stream!("input.txt")
    |> Stream.map(&String.trim_trailing/1)
    |> Stream.map(&String.split/1)
    |> Stream.map(&coerce/1)
    |> Enum.to_list
  end

  def check_condition(condition, value_a, value_b) do
    case condition do
        "<" -> (value_a < value_b)
        "<=" -> (value_a <= value_b)
        "==" -> (value_a == value_b)
        ">" -> (value_a > value_b)
        ">=" -> (value_a >= value_b)
        "!=" -> (value_a != value_b)
    end
  end

  def calculate_new_value(operation, value_a, value_b) do
    case operation do
      "inc" -> value_a + value_b
      "dec" -> value_a - value_b
    end
  end

  def update_max(earlier, new_value) do
    cond do
      (earlier > new_value) -> earlier
      true -> new_value
    end
  end


  def process(row, state) do
    [target, operation, value, condition_variable, condition, condition_value] = row
    cond do
      check_condition(condition, Map.get(state, condition_variable, 0), condition_value) ->
        new_value = calculate_new_value(operation, Map.get(state, target, 0), value)
        current_max = Map.get(state, :max, 0)
        Map.merge(state, %{target => new_value, :max => update_max(current_max, new_value)})
      true -> state
    end
  end

  def iterate_commands([head | tail], acc) do
    iterate_commands(tail, process(head, acc))
  end

  def iterate_commands([], acc) do
    acc
  end

  def solve_part_1() do
    state = iterate_commands(parse(), %{})
    values = Map.delete(state, :max)
             |> Map.values
    [Enum.max(values), Map.get(state, :max)]
  end

end
