package techdevguide.advanced.rain_lake_volume;

public class RainLakeVolume {

  public static int waterVolume(int... elevation) {
    int prev = 0;
    int i = 0;
    int volume = 0;
    while (i < elevation.length) {
      int curr = elevation[i];

      // Start of lake
      if (curr < prev) {

        // Find end of lake
        int j = i + 1;
        int max = 0;
        int maxi = i;
        while (true) {

          // End of map
          if (j >= elevation.length) {
            // Add lake volume
            for (int k = i; k < maxi; k++) {
              volume += max - elevation[k];
            }
            i = maxi + 1;
            curr = elevation[maxi];
            break;
          }

          // Record highest observed elevation
          int je = elevation[j];
          if (je > max) {
            max = je;
            maxi = j;
          }
          
          // End of lake
          if (je >= prev) {
            // Add lake volume
            for (int k = i; k < j; k++) {
              volume += prev - elevation[k];
            }
            i = j;
            curr = je;
            break;
          }

          j++;
        }
      }

      prev = curr;
      i++;
    }

    return volume;
  }

}
