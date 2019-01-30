package techdevguide.advanced.rain_lake_volume;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class RainLakeVolumeTest {

  @Test
  public void waterVolume() {
    assertThat(RainLakeVolume.waterVolume(1,3,2,4,1,3,1,4,5,2,2,1,4,2,2), is(15));
  }
}