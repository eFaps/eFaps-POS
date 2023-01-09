/*
 * Copyright 2003 - 2023 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.efaps.pos.dto;

public class PosPaymentDto extends PaymentDto {
  private final String collectOrderId;

  public String getCollectOrderId() {
    return collectOrderId;
  }

  private PosPaymentDto(Builder _builder) {
    super(_builder);
    this.collectOrderId = _builder.collectOrderId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder extends PaymentDto.Builder {
    private String collectOrderId;

    public Builder withCollectOrderId(final String collectOrderId) {
      this.collectOrderId = collectOrderId;
      return this;
    }

    @Override
    public PosPaymentDto build() {
      return new PosPaymentDto(this);
    }
  }
}
