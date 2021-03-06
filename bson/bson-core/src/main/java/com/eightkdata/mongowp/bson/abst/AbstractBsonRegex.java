/*
 * MongoWP
 * Copyright © 2014 8Kdata Technology (www.8kdata.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.eightkdata.mongowp.bson.abst;

import com.eightkdata.mongowp.bson.BsonRegex;
import com.eightkdata.mongowp.bson.BsonType;
import com.eightkdata.mongowp.bson.BsonValue;
import com.eightkdata.mongowp.bson.BsonValueVisitor;
import com.eightkdata.mongowp.bson.utils.BsonTypeComparator;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractBsonRegex extends AbstractBsonValue<BsonRegex> implements BsonRegex {

  @Override
  public Class<? extends BsonRegex> getValueClass() {
    return this.getClass();
  }

  @Override
  public String getOptionsAsText() {
    Set<Options> options = getOptions();
    if (options.isEmpty()) {
      return "";
    }
    if (options.size() == 1) {
      return Character.toString(options.iterator().next().getCharId());
    }

    SortedSet<Options> sortedOptions = new TreeSet<>(Options.getLexicographicalComparator());
    sortedOptions.addAll(options);

    StringBuilder sb = new StringBuilder(6);
    for (Options option : sortedOptions) {
      sb.append(option.getCharId());
    }
    return sb.toString();
  }

  @Override
  public BsonRegex getValue() {
    return this;
  }

  @Override
  public BsonType getType() {
    return BsonType.REGEX;
  }

  @Override
  public BsonRegex asRegex() {
    return this;
  }

  @Override
  public boolean isRegex() {
    return true;
  }

  @Override
  public int compareTo(BsonValue<?> obj) {
    if (obj == this) {
      return 0;
    }
    int diff = BsonTypeComparator.INSTANCE.compare(getType(), obj.getType());
    if (diff != 0) {
      return diff;
    }

    assert obj.isRegex();
    BsonRegex other = obj.asRegex();

    // TODO: Check how MongoDB compares documents!
    diff = this.getPattern().compareTo(other.getPattern());
    if (diff != 0) {
      return diff;
    }
    return this.getOptionsAsText().compareTo(other.getOptionsAsText());
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BsonRegex)) {
      return false;
    }
    BsonRegex other = (BsonRegex) obj;
    if (!this.getOptions().equals(other.getOptions())) {
      return false;
    }
    return this.getPattern().equals(other.getPattern());
  }

  @Override
  public final int hashCode() {
    return getPattern().hashCode();
  }

  @Override
  public <R, A> R accept(BsonValueVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
