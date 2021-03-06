package org.cloudera.htrace;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cloudera.htrace.impl.NullSpan;
import org.junit.Test;

public class TestSampler {
  @Test
  public void testParamterizedSampler() {
    TestParamSampler sampler = new TestParamSampler();
    Span s = Trace.startSpan("test", sampler, 1);
    assertFalse(s.equals(NullSpan.getInstance()));
    s.stop();
    s = Trace.startSpan("test", sampler, -1);
    assertTrue(s.equals(NullSpan.getInstance()));
    s.stop();
  }

  @Test
  public void testAlwaysSampler() {
    Span cur = Trace.startSpan("test", new TraceInfo(0, 0),
        Sampler.ALWAYS);
    assertFalse(cur.equals(NullSpan.getInstance()));
    cur.stop();
  }

  private class TestParamSampler implements Sampler<Integer> {

    @Override
    public boolean next(Integer info) {
      return info > 0;
    }

  }
}
