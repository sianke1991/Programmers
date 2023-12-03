package problem베스트앨범;
import java.util.*;

public class Q5_베스트앨범 {
	
	/**
	 * 노래 객체<br/>
	 * 각 객체는 장르명(String), 재생 횟수(int), 노래 번호(int)를 멤버 필드로 갖는다.
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
	 * 장르 객체<br/>
	 * 각 객체는 장르명(String), 총 재생 횟수(long)을 멤버 필드로 갖는다.
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
		/*각 장르에 대해 총 재생 횟수를 기록하는 Map 객체*/
		Map<String, Long> genresNumPlaysMap = new HashMap<>();
		/*각 장르에 대해 노래 목록을 보관하는 Map 객체*/
		Map<String, TreeSet<Song>> genresSongsMap = new HashMap<>();
		
		for (int i=0; i<genres.length; i++) {
			Long getResult = genresNumPlaysMap.get(genres[i]);
			//해당 장르를 처음 다루는 경우 Map 객체는 해당 장르의 entry가 아직 없다.
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
		// 장르를 총 재생 횟수에 대해 정렬한다.
		for (Map.Entry<String, Long> entry:genresNumPlaysMap.entrySet()) {
			genreSet.add(new Genre(entry));
		}
		//많이 재생된 장르부터 차례대로 돌면서 곡 2개씩을 선별하여 앨범에 넣는다.
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
