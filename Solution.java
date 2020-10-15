import java.util.*;
class Solution {
	public int[][] merge(int[][] intervals) {
		final int n = intervals.length;
		if (n == 0) {
			return new int[][]{};
		}
		
		
		Interval[] intervalsOOD = new Interval[n];
		for (int i = 0; i < n; i++) {
			intervalsOOD[i] = new Interval(intervals[i][0], intervals[i][1]);
		}
		
		Stack<Interval> resultsOOD = merge(intervalsOOD);
		
		final int m = resultsOOD.size();
		int[][] results = new int[m][2];
		int i = 0;
		for (Interval intervalOOD : resultsOOD) {
			results[i][0] = intervalOOD._start;
			results[i][1] = intervalOOD._end;
			i++;
		}
		return results;
	}
	
	private Stack<Interval> merge(Interval[] intervals) {
		Arrays.sort(intervals, (a,b)->Integer.compare(a._start,b._start));
		//Please note that I understand stack is abanden in industry. We should use double deque instead. But here is for demostation.
		Stack<Interval> stack = new Stack<>();
		stack.push(intervals[0]);
		for(int i = 0; i < intervals.length; i++) {
			Interval next = intervals[i];
			Interval pre = stack.peek();
			if(next._start <= pre._end) {
				pre._end = Math.max(next._end, pre._end);
			}else{
				stack.push(next);
			}
		}
		return stack;
	}
	private class Interval {
		int _start;
		int _end;
		public Interval(int start, int end) {
			_start = start;
			_end = end;
		}
		@Override 
		public String toString(){
			return "[" + _start + ", " + _end +"]";
		}
	}

	public boolean assertMySolution(int[][] expected, int[][] actual) {
		if(expected.length != actual.length) { 
			return false;
		}
		for(int i = 0; i < expected.length; i++) {
			if(expected[i].length != actual[i].length){
				return false;
			} 
			for(int j = 0; j < expected[i].length; j++) {
				if(expected[i][j] != actual[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	public static void main(String[] args) {
		//test case 
		Solution s = new Solution();
		int[][] test1 = new int[][]{{94133,94133},{94200,94299},{94600,94699}};
		int[][] test1Result = new int[][]{{94133,94133},{94200,94299},{94600,94699}};
		System.out.println(s.assertMySolution(test1Result, s.merge(test1)));		
		int[][] test2 = new int[][]{{94133,94133},{94200,94299},{94226,94399}};
		int[][] test2Result = new int[][]{{94133,94133},{94200,94399}};
		System.out.println(s.assertMySolution(test2Result, s.merge(test2)));		
	}
}
