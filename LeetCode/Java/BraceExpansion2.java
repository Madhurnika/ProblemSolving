class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = solve(expression, 0, expression.length() - 1);
        return new ArrayList<>(result);
    }
    private Set<String> solve(String s, int l, int r) {
        Set<String> result = new TreeSet<>();
        int balance = 0;
        int start = l;
        for (int i = l; i <= r; i++) {
            if (s.charAt(i) == '{') balance++;
            else if (s.charAt(i) == '}') balance--;

            if (balance == 0 && s.charAt(i) == ',') {
                result.addAll(solve(s, start, i - 1));
                start = i + 1;
            }
        }
        if (start != l) {
            result.addAll(solve(s, start, r));
            return result;
        }

        if (s.charAt(l) == '{' && s.charAt(r) == '}') {
            balance = 0;
            for (int i = l; i <= r; i++) {
                if (s.charAt(i) == '{') balance++;
                else if (s.charAt(i) == '}') balance--;

                if (balance == 0 && i < r) {
                    return concatenate(solve(s, l + 1, i - 1),
                                       solve(s, i + 1, r));
                }
            }
            return solve(s, l + 1, r - 1);
        }
        Set<String> resultSet = new TreeSet<>();
        resultSet.add("");
        int i = l;

        while (i <= r) {
            Set<String> part;
            if (s.charAt(i) == '{') {
                int balanceCount = 0;
                int j = i;
                while (j <= r) {
                    if (s.charAt(j) == '{') balanceCount++;
                    else if (s.charAt(j) == '}') balanceCount--;

                    if (balanceCount == 0) break;
                    j++;
                }
                part = solve(s, i + 1, j - 1);
                i = j + 1;
            } else {
                part = new TreeSet<>();
                part.add(String.valueOf(s.charAt(i)));
                i++;
            }
            resultSet = concatenate(resultSet, part);
        }
        return resultSet;
    }

    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>();
        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }
        return result;
    }
}
