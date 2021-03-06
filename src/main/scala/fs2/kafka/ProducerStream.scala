/*
 * Copyright 2018-2019 OVO Energy Limited
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
 */

package fs2.kafka

import cats.effect.ConcurrentEffect
import fs2.Stream

/**
  * [[ProducerStream]] provides support for inferring the key and value
  * type from [[ProducerSettings]] when using `producerStream` with the
  * following syntax.
  *
  * {{{
  * producerStream[F].using(settings)
  * }}}
  */
final class ProducerStream[F[_]] private[kafka] (
  private val F: ConcurrentEffect[F]
) extends AnyVal {

  /**
    * Creates a new [[KafkaProducer]] in the `Stream` context.
    * This is equivalent to using `producerStream` directly,
    * except we're able to infer the key and value type.
    */
  def using[K, V](settings: ProducerSettings[K, V]): Stream[F, KafkaProducer[F, K, V]] =
    producerStream(settings)(F)

  override def toString: String =
    "ProducerStream$" + System.identityHashCode(this)
}
