package me.wonwoo.elasticsearch

case class ElasticBuckets(key: String, value: Long){

  def getKey() = key
  def getValue() = value

}
