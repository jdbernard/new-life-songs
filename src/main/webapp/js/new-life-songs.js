(function() {

	var NLS = window.NewLifeSongs = {};

    //  #######################################################################
	/// ## Models
    //  #######################################################################

    /// ### SongModel
    NLS.SongModel = Backbone.Model.extend({ });

    /// ### ServiceModel
    NLS.ServiceModel = Backbone.Model.extend({ });

    /// ### PerformanceModel
    NLS.PerformanceModel = Backbone.Model.extend({ });

    //  #######################################################################
	/// ## Views
    //  #######################################################################

    /// ### SongsView
    NLS.SongsView = Backbone.View.extend({
        el: $("#songs-table")[0],

        initialize: function(options) { this.$el.dataTables(); }
    });

    /// ### ServicesView
    NLS.ServicesView = Backbone.View.extend({
        el: $("#services-table")[0],

        initialize: function(options) { this.$el.dataTables(); }
    });

})();
