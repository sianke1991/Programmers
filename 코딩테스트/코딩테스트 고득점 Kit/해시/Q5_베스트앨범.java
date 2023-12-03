package problem����Ʈ�ٹ�;
import java.util.*;

public class Q5_����Ʈ�ٹ� {
	
	/**
	 * �뷡 ��ü<br/>
	 * �� ��ü�� �帣��(String), ��� Ƚ��(int), �뷡 ��ȣ(int)�� ��� �ʵ�� ���´�.
	 */
	private static class Song implements Comparable<Song> {
		public final String genreName;
		public final int numPlays;
		public final int songNumber;
		
		public Song(String genreName, int numPlays, int songNumber) {
			this.genreName = genreName;
			this.numPlays = numPlays;
			this.songNumber = songNumber;
		}
		
		@Override
		public int compareTo(Song that) {
			return that.numPlays>this.numPlays ? 1 :
				   that.numPlays<this.numPlays ? -1 :
				   this.songNumber>that.songNumber ? 1 : -1;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o==null || !(o instanceof Song))
				return false;
			Song that = (Song) o;
			return this.songNumber == that.songNumber;
		}
	}
	
	/**
	 * �帣 ��ü<br/>
	 * �� ��ü�� �帣��(String), �� ��� Ƚ��(long)�� ��� �ʵ�� ���´�.
	 */
	private static class Genre implements Comparable<Genre> {
		public final String genreName;
		public final long numPlays;
		
		public Genre(Map.Entry<String, Long> entry) {
			this.genreName = entry.getKey();
			this.numPlays = entry.getValue();
		}
		
		@Override
		public int compareTo(Genre that) {
			return that.numPlays>this.numPlays ? 1 : -1;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o==null || !(o instanceof Genre))
				return false;
			Genre that = (Genre) o;
			return this.genreName.equals(that.genreName);
		}
	}
	
	public int[] solution(String[] genres, int[] plays) {
		/*�� �帣�� ���� �� ��� Ƚ���� ����ϴ� Map ��ü*/
		Map<String, Long> genresNumPlaysMap = new HashMap<>();
		/*�� �帣�� ���� �뷡 ����� �����ϴ� Map ��ü*/
		Map<String, TreeSet<Song>> genresSongsMap = new HashMap<>();
		
		for (int i=0; i<genres.length; i++) {
			Long getResult = genresNumPlaysMap.get(genres[i]);
			//�ش� �帣�� ó�� �ٷ�� ��� Map ��ü�� �ش� �帣�� entry�� ���� ����.
			if (getResult==null) {
				genresNumPlaysMap.put(genres[i], (long)plays[i]);
				genresSongsMap.put(genres[i], new TreeSet<>());
				genresSongsMap.get(genres[i]).add(new Song(genres[i], plays[i], i));
			} else {
				genresNumPlaysMap.put(genres[i], getResult+plays[i]);
				genresSongsMap.get(genres[i]).add(new Song(genres[i], plays[i], i));
			}
		}
		
		List<Integer> selectedSongNumbers = new ArrayList<>();
		Set<Genre> genreSet = new TreeSet<>();
		// �帣�� �� ��� Ƚ���� ���� �����Ѵ�.
		for (Map.Entry<String, Long> entry:genresNumPlaysMap.entrySet()) {
			genreSet.add(new Genre(entry));
		}
		//���� ����� �帣���� ���ʴ�� ���鼭 �� 2������ �����Ͽ� �ٹ��� �ִ´�.
		for (Genre genre:genreSet) {
			int numSelectedSongs = 0;
			for (Song song:genresSongsMap.get(genre.genreName)) {
				selectedSongNumbers.add(song.songNumber);
				numSelectedSongs++;
				if (numSelectedSongs>=2) break;
			}
		}
		
		int[] answer = new int[selectedSongNumbers.size()];
		for (int i=0; i<answer.length; i++)
			answer[i] = selectedSongNumbers.get(i);
		return answer;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
